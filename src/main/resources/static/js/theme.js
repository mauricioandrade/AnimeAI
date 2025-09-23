(function () {
    const storageKey = 'animeai-theme';
    const body = document.body;
    const toggle = document.getElementById('theme-toggle');

    if (!toggle) {
        return;
    }

    const icon = toggle.querySelector('.theme-toggle-icon');
    const text = toggle.querySelector('.theme-toggle-text');

    const applyTheme = (theme) => {
        const isDark = theme === 'dark';
        body.classList.toggle('dark-mode', isDark);
        toggle.setAttribute('aria-pressed', isDark ? 'true' : 'false');

        if (icon) {
            icon.textContent = isDark ? '☀️' : '🌙';
        }

        if (text) {
            text.textContent = isDark ? 'Modo claro' : 'Modo escuro';
        }
    };

    const getPreferredTheme = () => {
        try {
            const stored = localStorage.getItem(storageKey);
            if (stored === 'dark' || stored === 'light') {
                return stored;
            }
        } catch (error) {
            // Ignored: localStorage might be unavailable (e.g., private mode).
        }

        if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
            return 'dark';
        }

        return 'light';
    };

    const setStoredTheme = (theme) => {
        try {
            localStorage.setItem(storageKey, theme);
        } catch (error) {
            // Ignored: localStorage might be unavailable (e.g., private mode).
        }
    };

    applyTheme(getPreferredTheme());

    toggle.addEventListener('click', () => {
        const isDark = body.classList.contains('dark-mode');
        const newTheme = isDark ? 'light' : 'dark';
        applyTheme(newTheme);
        setStoredTheme(newTheme);
    });

    if (window.matchMedia) {
        const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
        const handleChange = (event) => {
            try {
                const stored = localStorage.getItem(storageKey);
                if (stored === 'dark' || stored === 'light') {
                    return;
                }
            } catch (error) {
                // Ignore storage access errors and respect system preference.
            }

            applyTheme(event.matches ? 'dark' : 'light');
        };

        if (mediaQuery.addEventListener) {
            mediaQuery.addEventListener('change', handleChange);
        } else if (mediaQuery.addListener) {
            mediaQuery.addListener(handleChange);
        }
    }
})();
