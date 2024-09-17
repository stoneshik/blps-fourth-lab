INSERT INTO roles(id, name)
VALUES
    (default, 'ROLE_ADMIN'),
    (default, 'ROLE_USER')
    ;
INSERT INTO tax_regimes (
    id,
    title,
    description,
    max_annual_income_thousands,
    max_number_employees)
VALUES
    (default,  -- 1
     'УСН (доходы) для ИП\n',
     'Необходимость регистрации в качестве ИП - Да\n' ||
     'Представление уведомления о переходе на УСН - Да (не позднее последнего числа года, предшествующего году, с которого планируется начать применение УСН)\n',
     200000,
     130
     ),
    (default,  -- 2
     'УСН (доходы минус расходы) для ИП',
     'Необходимость регистрации в качестве ИП - Да\n' ||
     'Представление уведомления о переходе на УСН - Да (не позднее последнего числа года, предшествующего году, с которого планируется начать применение УСН)\n',
     200000,
     130
    ),
    (default,  -- 3
     'ПСН',
     'Необходимость регистрации в качестве ИП - Да\n' ||
     'Необходимость подачи заявления на получение патента - Да\n' ||
     'Налоговая ставка - 6%\n' ||
     'Отчетность - Не представляется\n',
     60000,
     15
     ),
    (default,  -- 4
     'НПД',
     'Необходимость регистрации в качестве ИП - Нет\n' ||
     'Как стать налогоплательщиком налога на профессиональный доход - Зарегистрироваться в качестве налогоплательщика можно, скачав мобильное приложение «Мой налог» или через web-кабинет «Мой налог»\n',
     2400,
     0
    ),
    (default,  -- 5
     'УСН (доходы минус расходы) для ЮЛ',
     'Представление уведомления о переходе на УСН - Да (не позднее последнего числа года, предшествующего году, с которого планируется начать применение УСН)\n',
     200000,
     130
    ),
    (default,  -- 6
     'УСН (доходы) для ЮЛ',
     'Представление уведомления о переходе на УСН - Да (не позднее последнего числа года, предшествующего году, с которого планируется начать применение УСН)\n',
     200000,
     130
    ),
    (default,  -- 7
     'ОРН для ЮЛ',
     'Наличие регистрации в качестве ЮЛ - Да\n',
     NULL,
     NULL
    ),
    (default,  -- 8
     'ОРН для ИП',
     'Наличие регистрации в качестве ИП - Да\n',
     NULL,
     NULL
    ),
    (default,  -- 9
     'АВТОУСН',
     'Необходимость регистрации в качестве ИП - Да\n' ||
     'Как начать применять АУСН:\n' ||
     'Эксперимент проводится в г. Москве, Московской и Калужской областях и Республике Татарстан\n',
     60000,
     5
    )
    ;

INSERT INTO tax_features(id, tax_regime_id, tax_feature)
VALUES
    (default, 7, 'PRODUCTION_EXCISABLE_GOODS'),  -- ОРН для ЮЛ
    (default, 8, 'PRODUCTION_EXCISABLE_GOODS'),  -- ОРН для ИП
    (default, 4, 'NO_NEED_KEEP_TAX_RECORDS'),  -- НПД
    (default, 9, 'NO_NEED_KEEP_TAX_RECORDS'),  -- АВТОУСН
    (default, 3, 'NO_OBLIGATION_SUBMIT_DECLARATIONS'),  -- ПСН
    (default, 4, 'NO_OBLIGATION_SUBMIT_DECLARATIONS'),  -- НПД
    (default, 9, 'NO_OBLIGATION_SUBMIT_DECLARATIONS')  -- АВТОУСН
    ;

INSERT INTO taxpayer_category(id, tax_regime_id, taxpayer_category)
VALUES
    (default, 1, 'INDIVIDUAL_ENTREPRENEUR'),  -- УСН (доходы) для ИП
    (default, 2, 'INDIVIDUAL_ENTREPRENEUR'),  -- УСН (доходы минус расходы) для ИП
    (default, 3, 'INDIVIDUAL_ENTREPRENEUR'),  -- ПСН
    (default, 4, 'INDIVIDUAL_ENTREPRENEUR'),  -- НПД
    (default, 8, 'INDIVIDUAL_ENTREPRENEUR'),  -- ОРН для ИП
    (default, 9, 'INDIVIDUAL_ENTREPRENEUR'),  -- АВТОУСН
    (default, 5, 'LEGAL_ENTITY'),  -- УСН (доходы минус расходы) для ЮЛ
    (default, 6, 'LEGAL_ENTITY'),  -- УСН (доходы) для ЮЛ
    (default, 7, 'LEGAL_ENTITY'),  -- ОРН для ЮЛ
    (default, 9, 'LEGAL_ENTITY'),  -- АВТОУСН
    (default, 4, 'INDIVIDUAL')  -- НПД
    ;

INSERT INTO users(id, amount_request, description, login, name, password, surname)
VALUES
    (default, 30, 'user admin', 'admin', 'admin', '', 'admin'),
    (default, 30, 'user first', 'first', 'first', '', 'first')
    ;
