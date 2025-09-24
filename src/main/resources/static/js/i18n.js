(function () {
    const storageKey = 'animeai-language';
    const defaultLanguage = 'pt';
    const supportedLanguages = new Set(['pt', 'en', 'es', 'ja']);
    const localeMap = {
        pt: 'pt-BR',
        en: 'en',
        es: 'es',
        ja: 'ja'
    };

    const translations = {
        pt: {
            'language.portuguese': 'Português',
            'language.english': 'Inglês',
            'language.spanish': 'Espanhol',
            'language.japanese': 'Japonês',
            'language.switcher.label': 'Seleção de idioma',
            'theme.toggle.title': 'Alternar tema',
            'theme.toggle.light': 'Modo claro',
            'theme.toggle.dark': 'Modo escuro',
            'list.meta.title': 'AnimeAI - Catálogo de Animes',
            'list.header.description': 'Gerencie sua coleção de animes e peça sugestões personalizadas.',
            'list.actions.new': 'Cadastrar novo anime',
            'list.actions.suggestion': 'Quero uma sugestão',
            'list.section.title': 'Meus animes',
            'list.table.title': 'Título',
            'list.table.category': 'Categoria',
            'list.table.releaseYear': 'Ano de lançamento',
            'list.table.episodes': 'Episódios',
            'list.table.actions': 'Ações',
            'list.table.edit': 'Editar',
            'list.table.delete': 'Excluir',
            'list.confirm.delete': 'Tem certeza que deseja remover este anime?',
            'list.empty': 'Nenhum anime cadastrado até o momento.',
            'layout.footer': 'AnimeAI — Powered by Spring Boot e Thymeleaf.',
            'form.meta.title.new': 'Cadastrar anime',
            'form.meta.title.edit': 'Editar anime',
            'form.header.title.new': 'Cadastrar anime',
            'form.header.title.edit': 'Editar anime',
            'form.header.description': 'Preencha os dados abaixo e mantenha sua lista sempre atualizada.',
            'form.field.title': 'Título',
            'form.placeholder.title': 'Ex: Fullmetal Alchemist',
            'form.field.category': 'Categoria',
            'form.select.placeholder': 'Selecione',
            'form.field.releaseYear': 'Ano de lançamento',
            'form.field.episodes': 'Número de episódios',
            'form.placeholder.episodes': 'Ex: 24',
            'form.actions.save': 'Salvar',
            'form.actions.cancel': 'Cancelar',
            'suggestion.meta.title': 'Sugestões de animes',
            'suggestion.header.title': 'Sugestão personalizada',
            'suggestion.header.description': 'Baseado na sua lista atual, aqui estão algumas recomendações.',
            'suggestion.section.title': 'Resultado',
            'suggestion.actions.back': 'Voltar para a lista'
        },
        en: {
            'language.portuguese': 'Portuguese',
            'language.english': 'English',
            'language.spanish': 'Spanish',
            'language.japanese': 'Japanese',
            'language.switcher.label': 'Language selection',
            'theme.toggle.title': 'Toggle theme',
            'theme.toggle.light': 'Light mode',
            'theme.toggle.dark': 'Dark mode',
            'list.meta.title': 'AnimeAI - Anime Catalog',
            'list.header.description': 'Manage your anime collection and request personalized suggestions.',
            'list.actions.new': 'Add new anime',
            'list.actions.suggestion': 'Give me a suggestion',
            'list.section.title': 'My anime',
            'list.table.title': 'Title',
            'list.table.category': 'Category',
            'list.table.releaseYear': 'Release year',
            'list.table.episodes': 'Episodes',
            'list.table.actions': 'Actions',
            'list.table.edit': 'Edit',
            'list.table.delete': 'Delete',
            'list.confirm.delete': 'Are you sure you want to remove this anime?',
            'list.empty': 'No anime added yet.',
            'layout.footer': 'AnimeAI — Powered by Spring Boot and Thymeleaf.',
            'form.meta.title.new': 'Add anime',
            'form.meta.title.edit': 'Edit anime',
            'form.header.title.new': 'Add anime',
            'form.header.title.edit': 'Edit anime',
            'form.header.description': 'Fill out the information below to keep your list up to date.',
            'form.field.title': 'Title',
            'form.placeholder.title': 'e.g., Fullmetal Alchemist',
            'form.field.category': 'Category',
            'form.select.placeholder': 'Select',
            'form.field.releaseYear': 'Release year',
            'form.field.episodes': 'Number of episodes',
            'form.placeholder.episodes': 'e.g., 24',
            'form.actions.save': 'Save',
            'form.actions.cancel': 'Cancel',
            'suggestion.meta.title': 'Anime suggestions',
            'suggestion.header.title': 'Personalized suggestion',
            'suggestion.header.description': 'Based on your current list, here are some recommendations.',
            'suggestion.section.title': 'Result',
            'suggestion.actions.back': 'Back to the list'
        },
        es: {
            'language.portuguese': 'Portugués',
            'language.english': 'Inglés',
            'language.spanish': 'Español',
            'language.japanese': 'Japonés',
            'language.switcher.label': 'Selección de idioma',
            'theme.toggle.title': 'Cambiar tema',
            'theme.toggle.light': 'Modo claro',
            'theme.toggle.dark': 'Modo oscuro',
            'list.meta.title': 'AnimeAI - Catálogo de anime',
            'list.header.description': 'Administra tu colección de anime y solicita sugerencias personalizadas.',
            'list.actions.new': 'Agregar nuevo anime',
            'list.actions.suggestion': 'Quiero una sugerencia',
            'list.section.title': 'Mis animes',
            'list.table.title': 'Título',
            'list.table.category': 'Categoría',
            'list.table.releaseYear': 'Año de lanzamiento',
            'list.table.episodes': 'Episodios',
            'list.table.actions': 'Acciones',
            'list.table.edit': 'Editar',
            'list.table.delete': 'Eliminar',
            'list.confirm.delete': '¿Seguro que deseas eliminar este anime?',
            'list.empty': 'No hay animes registrados por ahora.',
            'layout.footer': 'AnimeAI — Impulsado por Spring Boot y Thymeleaf.',
            'form.meta.title.new': 'Registrar anime',
            'form.meta.title.edit': 'Editar anime',
            'form.header.title.new': 'Registrar anime',
            'form.header.title.edit': 'Editar anime',
            'form.header.description': 'Completa los datos a continuación y mantén tu lista siempre actualizada.',
            'form.field.title': 'Título',
            'form.placeholder.title': 'Ej: Fullmetal Alchemist',
            'form.field.category': 'Categoría',
            'form.select.placeholder': 'Selecciona',
            'form.field.releaseYear': 'Año de lanzamiento',
            'form.field.episodes': 'Número de episodios',
            'form.placeholder.episodes': 'Ej: 24',
            'form.actions.save': 'Guardar',
            'form.actions.cancel': 'Cancelar',
            'suggestion.meta.title': 'Sugerencias de anime',
            'suggestion.header.title': 'Sugerencia personalizada',
            'suggestion.header.description': 'Con base en tu lista actual, aquí tienes algunas recomendaciones.',
            'suggestion.section.title': 'Resultado',
            'suggestion.actions.back': 'Volver a la lista'
        },
        ja: {
            'language.portuguese': 'ポルトガル語',
            'language.english': '英語',
            'language.spanish': 'スペイン語',
            'language.japanese': '日本語',
            'language.switcher.label': '言語の選択',
            'theme.toggle.title': 'テーマを切り替える',
            'theme.toggle.light': 'ライトモード',
            'theme.toggle.dark': 'ダークモード',
            'list.meta.title': 'AnimeAI - アニメカタログ',
            'list.header.description': 'アニメコレクションを管理して、あなたに合わせたおすすめを受け取りましょう。',
            'list.actions.new': '新しいアニメを登録',
            'list.actions.suggestion': 'おすすめが欲しい',
            'list.section.title': 'マイアニメ',
            'list.table.title': 'タイトル',
            'list.table.category': 'カテゴリー',
            'list.table.releaseYear': '公開年',
            'list.table.episodes': 'エピソード数',
            'list.table.actions': '操作',
            'list.table.edit': '編集',
            'list.table.delete': '削除',
            'list.confirm.delete': 'このアニメを削除してもよろしいですか？',
            'list.empty': 'まだアニメが登録されていません。',
            'layout.footer': 'AnimeAI — Spring Boot と Thymeleaf で動作しています。',
            'form.meta.title.new': 'アニメを登録',
            'form.meta.title.edit': 'アニメを編集',
            'form.header.title.new': 'アニメを登録',
            'form.header.title.edit': 'アニメを編集',
            'form.header.description': '以下の情報を入力して、リストを常に最新の状態に保ちましょう。',
            'form.field.title': 'タイトル',
            'form.placeholder.title': '例: 鋼の錬金術師',
            'form.field.category': 'カテゴリー',
            'form.select.placeholder': '選択してください',
            'form.field.releaseYear': '公開年',
            'form.field.episodes': 'エピソード数',
            'form.placeholder.episodes': '例: 24',
            'form.actions.save': '保存',
            'form.actions.cancel': 'キャンセル',
            'suggestion.meta.title': 'アニメのおすすめ',
            'suggestion.header.title': 'パーソナライズされたおすすめ',
            'suggestion.header.description': '現在のリストに基づき、いくつかのおすすめをご紹介します。',
            'suggestion.section.title': '結果',
            'suggestion.actions.back': '一覧に戻る'
        }
    };

    let currentLanguage = defaultLanguage;

    const translate = (key, lang = currentLanguage) => {
        const languagePack = translations[lang];
        if (languagePack && Object.prototype.hasOwnProperty.call(languagePack, key)) {
            return languagePack[key];
        }

        if (lang !== defaultLanguage) {
            const fallbackPack = translations[defaultLanguage];
            if (fallbackPack && Object.prototype.hasOwnProperty.call(fallbackPack, key)) {
                return fallbackPack[key];
            }
        }

        return key;
    };

    const getStoredLanguage = () => {
        try {
            const stored = localStorage.getItem(storageKey);
            if (stored && supportedLanguages.has(stored)) {
                return stored;
            }
        } catch (error) {
            // Ignored: storage might be unavailable.
        }
        return defaultLanguage;
    };

    const persistLanguage = (lang) => {
        try {
            localStorage.setItem(storageKey, lang);
        } catch (error) {
            // Ignored: storage might be unavailable.
        }
    };

    const updateLanguageButtonsState = () => {
        const buttons = document.querySelectorAll('.language-button[data-language]');
        buttons.forEach((button) => {
            const isActive = button.dataset.language === currentLanguage;
            button.setAttribute('aria-pressed', isActive ? 'true' : 'false');
        });
    };

    const updateThemeToggleLabels = () => {
        const toggle = document.getElementById('theme-toggle');
        if (!toggle) {
            return;
        }

        const textElement = toggle.querySelector('.theme-toggle-text');
        if (!textElement) {
            return;
        }

        const lightKey = textElement.dataset.labelLightKey;
        const darkKey = textElement.dataset.labelDarkKey;

        if (lightKey) {
            textElement.dataset.labelLight = translate(lightKey);
        }
        if (darkKey) {
            textElement.dataset.labelDark = translate(darkKey);
        }

        const isDark = document.body.classList.contains('dark-mode');
        const label = isDark ? textElement.dataset.labelLight : textElement.dataset.labelDark;
        if (label) {
            textElement.textContent = label;
        }

        const titleKey = toggle.dataset.i18nTitle;
        if (titleKey) {
            toggle.setAttribute('title', translate(titleKey));
        }
    };

    const applyTranslations = () => {
        const locale = localeMap[currentLanguage] || currentLanguage;
        document.documentElement.setAttribute('lang', locale);

        const textElements = document.querySelectorAll('[data-i18n]');
        textElements.forEach((element) => {
            const key = element.dataset.i18n;
            if (!key) {
                return;
            }
            const value = translate(key);
            element.textContent = value;
        });

        const placeholderElements = document.querySelectorAll('[data-i18n-placeholder]');
        placeholderElements.forEach((element) => {
            const key = element.dataset.i18nPlaceholder;
            if (!key) {
                return;
            }
            const value = translate(key);
            element.setAttribute('placeholder', value);
        });

        const dataLabelElements = document.querySelectorAll('[data-i18n-label]');
        dataLabelElements.forEach((element) => {
            const key = element.dataset.i18nLabel;
            if (!key) {
                return;
            }
            const value = translate(key);
            element.dataset.label = value;
            element.setAttribute('data-label', value);
        });

        const titleElements = document.querySelectorAll('[data-i18n-title]');
        titleElements.forEach((element) => {
            const key = element.dataset.i18nTitle;
            if (!key) {
                return;
            }
            element.setAttribute('title', translate(key));
        });

        const ariaLabelElements = document.querySelectorAll('[data-i18n-aria-label]');
        ariaLabelElements.forEach((element) => {
            const key = element.dataset.i18nAriaLabel;
            if (!key) {
                return;
            }
            element.setAttribute('aria-label', translate(key));
        });

        const documentTitleElement = document.querySelector('title[data-i18n-document-title]');
        if (documentTitleElement) {
            const key = documentTitleElement.dataset.i18nDocumentTitle;
            if (key) {
                document.title = translate(key);
            }
        }

        updateThemeToggleLabels();
        updateLanguageButtonsState();
        document.dispatchEvent(new CustomEvent('animeai:languagechange', {
            detail: { language: currentLanguage }
        }));
    };

    const setLanguage = (lang, options = {}) => {
        const desiredLanguage = supportedLanguages.has(lang) ? lang : defaultLanguage;
        const shouldPersist = options.persist !== false;
        const force = options.force === true;

        if (!force && desiredLanguage === currentLanguage) {
            updateLanguageButtonsState();
            return;
        }

        currentLanguage = desiredLanguage;

        if (shouldPersist) {
            persistLanguage(currentLanguage);
        }

        applyTranslations();
    };

    const handleLanguageButtonClick = (event) => {
        const button = event.currentTarget;
        const lang = button.dataset.language;
        setLanguage(lang);
    };

    const bindLanguageButtons = () => {
        const buttons = document.querySelectorAll('.language-button[data-language]');
        buttons.forEach((button) => {
            button.addEventListener('click', handleLanguageButtonClick);
        });
    };

    const setupDeleteConfirmation = () => {
        const buttons = document.querySelectorAll('.js-delete-button[data-confirm-key]');
        buttons.forEach((button) => {
            if (button.dataset.confirmListenerAttached === 'true') {
                return;
            }
            button.dataset.confirmListenerAttached = 'true';

            button.addEventListener('click', (event) => {
                const key = button.dataset.confirmKey;
                const message = translate(key);
                if (!message) {
                    return;
                }
                if (!window.confirm(message)) {
                    event.preventDefault();
                }
            });
        });
    };

    document.addEventListener('DOMContentLoaded', () => {
        window.AnimeAI = window.AnimeAI || {};
        window.AnimeAI.translate = translate;
        window.AnimeAI.setLanguage = (lang) => setLanguage(lang);
        window.AnimeAI.getLanguage = () => currentLanguage;

        currentLanguage = getStoredLanguage();

        bindLanguageButtons();
        setupDeleteConfirmation();
        setLanguage(currentLanguage, { persist: false, force: true });
    });
})();
