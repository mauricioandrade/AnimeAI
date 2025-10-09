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
                if (window.innerWidth < 768) {
                    closeMenu();
                }
            });
        });

        const breakpoint = window.matchMedia('(min-width: 768px)');
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

(function () {
    const switchers = Array.from(document.querySelectorAll('.language-switcher'));
    if (!switchers.length) {
        return;
    }

    const closeSwitcher = (switcher) => {
        const toggle = switcher.querySelector('.language-toggle');
        const menu = switcher.querySelector('.language-menu');
        if (!toggle) {
            return;
        }
        switcher.classList.remove('is-open');
        toggle.setAttribute('aria-expanded', 'false');
        if (menu) {
            menu.setAttribute('aria-hidden', 'true');
        }
    };

    const closeAll = () => {
        switchers.forEach((switcher) => closeSwitcher(switcher));
    };

    const updateSummary = (switcher) => {
        const toggle = switcher.querySelector('.language-toggle');
        if (!toggle) {
            return;
        }

        const labelElement = toggle.querySelector('.language-toggle-label');
        const flagElement = toggle.querySelector('.language-toggle-flag .language-icon');
        const activeButton = switcher.querySelector('.language-button[aria-pressed="true"]');

        if (!activeButton) {
            if (labelElement && labelElement.dataset.defaultLabel) {
                labelElement.textContent = labelElement.dataset.defaultLabel;
            }
            return;
        }

        const buttonLabel = activeButton.querySelector('.language-label');
        if (labelElement && buttonLabel) {
            labelElement.textContent = buttonLabel.textContent.trim();
        }

        const buttonIcon = activeButton.querySelector('.language-icon');
        if (flagElement && buttonIcon) {
            flagElement.src = buttonIcon.getAttribute('src') || '';
            flagElement.alt = buttonIcon.getAttribute('alt') || '';
        }
    };

    const handleDocumentClick = (event) => {
        if (switchers.some((switcher) => switcher.contains(event.target))) {
            return;
        }
        closeAll();
    };

    const handleKeydown = (event) => {
        if (event.key === 'Escape' || event.key === 'Esc') {
            closeAll();
        }
    };

    document.addEventListener('click', handleDocumentClick);
    document.addEventListener('keydown', handleKeydown);

    const breakpoint = window.matchMedia('(min-width: 768px)');
    const handleBreakpointChange = (event) => {
        if (event.matches) {
            closeAll();
        }
    };

    if (breakpoint.addEventListener) {
        breakpoint.addEventListener('change', handleBreakpointChange);
    } else if (breakpoint.addListener) {
        breakpoint.addListener(handleBreakpointChange);
    }

    switchers.forEach((switcher) => {
        const toggle = switcher.querySelector('.language-toggle');
        const menu = switcher.querySelector('.language-menu');
        if (!toggle || !menu) {
            return;
        }

        menu.setAttribute('aria-hidden', 'true');

        const focusToggle = () => {
            if (typeof toggle.focus !== 'function') {
                return;
            }
            try {
                toggle.focus({ preventScroll: true });
            } catch (error) {
                toggle.focus();
            }
        };

        const openMenu = () => {
            const isOpen = switcher.classList.contains('is-open');
            if (isOpen) {
                closeSwitcher(switcher);
                focusToggle();
                return;
            }

            closeAll();
            switcher.classList.add('is-open');
            toggle.setAttribute('aria-expanded', 'true');
            menu.setAttribute('aria-hidden', 'false');

            window.requestAnimationFrame(() => {
                const active = switcher.querySelector('.language-button[aria-pressed="true"]');
                if (active) {
                    active.focus();
                }
            });
        };

        toggle.addEventListener('click', (event) => {
            event.preventDefault();
            openMenu();
        });

        toggle.addEventListener('keydown', (event) => {
            if (event.key === 'ArrowDown') {
                openMenu();
                event.preventDefault();
            }
        });

        switcher.querySelectorAll('.language-button').forEach((button) => {
            button.addEventListener('click', () => {
                closeSwitcher(switcher);
                updateSummary(switcher);
                focusToggle();
            });
        });

        updateSummary(switcher);
        toggle.setAttribute('aria-expanded', 'false');
    });

    document.addEventListener('animeai:languagechange', () => {
        switchers.forEach(updateSummary);
    });
})();
