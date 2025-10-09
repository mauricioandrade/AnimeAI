(function () {
    const toggles = document.querySelectorAll('.menu-toggle');

    toggles.forEach((button) => {
        const controlId = button.getAttribute('aria-controls');
        const target = controlId ? document.getElementById(controlId) : null;

        if (!target) {
            return;
        }

        const openTitle = button.getAttribute('title') || 'Abrir menu';
        const closeTitle = button.getAttribute('data-close-title') || 'Fechar menu';

        const setMenuState = (expanded) => {
            button.classList.toggle('is-active', expanded);
            button.setAttribute('aria-expanded', String(expanded));
            if (target) {
                target.classList.toggle('is-open', expanded);
            }
            if (openTitle) {
                button.setAttribute('title', expanded ? closeTitle : openTitle);
            }
        };

        const closeMenu = () => setMenuState(false);

        const toggleMenu = () => {
            const isExpanded = button.getAttribute('aria-expanded') === 'true';
            setMenuState(!isExpanded);
        };

        button.addEventListener('click', toggleMenu);

        target.querySelectorAll('a').forEach((link) => {
            link.addEventListener('click', () => {
                if (window.innerWidth < 1024) {
                    closeMenu();
                }
            });
        });

        const breakpoint = window.matchMedia('(min-width: 1024px)');
        const handleBreakpointChange = (event) => {
            if (event.matches) {
                closeMenu();
            }
        };

        if (breakpoint.addEventListener) {
            breakpoint.addEventListener('change', handleBreakpointChange);
        } else if (breakpoint.addListener) {
            breakpoint.addListener(handleBreakpointChange);
        }

        // Ensure menu starts closed in case markup sets aria-expanded differently
        closeMenu();
    });
})();
