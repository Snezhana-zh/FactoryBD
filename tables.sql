CREATE TYPE employee_position AS ENUM('worker', 'engineer', 'tester');

CREATE TABLE company (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    position employee_position NOT NULL
);

CREATE TABLE department (
    id SERIAL PRIMARY KEY,
    head INTEGER REFERENCES employee(id),
    name TEXT,
    company_id INTEGER REFERENCES company(id)
);

CREATE TABLE test_lab (
    id SERIAL PRIMARY KEY,
    title TEXT
);

CREATE TABLE test_lab_equipment (
    id SERIAL PRIMARY KEY,
    title TEXT,
    test_lab_id INTEGER REFERENCES test_lab(id)
);

CREATE TABLE tlab_dep (
    test_lab_id INTEGER REFERENCES test_lab(id),
    department_id INTEGER REFERENCES department(id),
    PRIMARY KEY (test_lab_id, department_id)
);

CREATE TABLE product_category (
    id SERIAL PRIMARY KEY,
    title TEXT,
    attr JSON
);

CREATE TABLE product_model (
    id SERIAL PRIMARY KEY,
    title TEXT,
    category_id INTEGER REFERENCES product_category(id),
    department_id INTEGER REFERENCES department(id)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    model_id INTEGER REFERENCES product_model(id)
);

CREATE TABLE workshop (
    id SERIAL PRIMARY KEY,
    department_id INTEGER REFERENCES department(id),
    name TEXT,
    head INTEGER REFERENCES employee(id)
);

CREATE TABLE brigade (
    id SERIAL PRIMARY KEY,
    head INTEGER REFERENCES employee(id),
    workshop_id INTEGER REFERENCES workshop(id)
);

CREATE TABLE workers (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER REFERENCES employee(id),
    brigade_id INTEGER REFERENCES brigade(id)
);

CREATE TABLE engineers (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER REFERENCES employee(id),
    workshop_id INTEGER REFERENCES workshop(id)
);

CREATE TABLE testers (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER REFERENCES employee(id),
    test_lab_id INTEGER REFERENCES test_lab(id)
);

CREATE TABLE product_logs (
    id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES product(id),
    start_work TIMESTAMP NOT NULL,
    end_work TIMESTAMP,
    workshop_id INTEGER REFERENCES workshop(id),
    testlab_id INTEGER REFERENCES test_lab(id),
    CHECK (
        workshop_id IS NOT NULL OR testlab_id IS NOT NULL
    )
);

CREATE TABLE employee_logs (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER REFERENCES employee(id),
    position employee_position NOT NULL,
    start_work TIMESTAMP NOT NULL,
    end_work TIMESTAMP
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE user_accounts (
    id SERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE user_roles (
    user_id INTEGER REFERENCES user_accounts(id) ON DELETE CASCADE,
    role_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- CREATE TYPE employee_position AS ENUM('worker', 'engineer', 'tester');

-- CREATE TABLE company (
--     id UUID PRIMARY KEY,
--     name TEXT
-- );

-- CREATE TABLE employee (
--     id UUID PRIMARY KEY,
--     name TEXT NOT NULL,
--     position employee_position NOT NULL
-- );

-- CREATE TABLE department (
--     id UUID PRIMARY KEY,
--     head UUID REFERENCES employee(id),
--     name TEXT,
--     company_id UUID REFERENCES company(id)
-- );

-- CREATE TABLE test_lab (
--     id UUID PRIMARY KEY,
--     title TEXT
-- );

-- CREATE TABLE test_lab_equipment (
--     id UUID PRIMARY KEY,
--     title TEXT,
--     test_lab_id UUID REFERENCES test_lab(id)
-- );

-- CREATE TABLE tlab_dep (
--     test_lab_id UUID REFERENCES test_lab(id),
--     department_id UUID REFERENCES department(id),
--     PRIMARY KEY (test_lab_id, department_id)
-- );

-- CREATE TABLE product_category (
--     id UUID PRIMARY KEY,
--     title TEXT,
--     attr JSON
-- );

-- CREATE TABLE product_model (
--     id UUID PRIMARY KEY,
--     title TEXT,
--     category_id UUID REFERENCES product_category(id),
--     department_id UUID REFERENCES department(id)
-- );

-- CREATE TABLE product (
--     id UUID PRIMARY KEY,
--     model_id UUID REFERENCES product_model(id)
-- );

-- CREATE TABLE workshop (
--     id UUID PRIMARY KEY,
--     department_id UUID REFERENCES department(id),
--     name TEXT,
--     head UUID REFERENCES employee(id)
-- );

-- CREATE TABLE brigade (
--     id UUID PRIMARY KEY,
--     head UUID REFERENCES employee(id),
--     workshop_id UUID REFERENCES workshop(id)
-- );

-- CREATE TABLE workers (
--     id UUID PRIMARY KEY,
--     employee_id UUID REFERENCES employee(id),
--     brigade_id UUID REFERENCES brigade(id)
-- );

-- CREATE TABLE engineers (
--     id UUID PRIMARY KEY,
--     employee_id UUID REFERENCES employee(id),
--     workshop_id UUID REFERENCES workshop(id)
-- );

-- CREATE TABLE testers (
--     id UUID PRIMARY KEY,
--     employee_id UUID REFERENCES employee(id),
--     test_lab_id UUID REFERENCES test_lab(id)
-- );

-- CREATE TABLE product_logs (
--     id UUID PRIMARY KEY,
--     product_id UUID REFERENCES product(id),
--     -- status TEXT CHECK (status IN ('done', 'in_progress', 'test')),
--     start_work TIMESTAMP NOT NULL,
--     end_work TIMESTAMP,
--     workshop_id UUID REFERENCES workshop(id),
--     testlab_id UUID REFERENCES test_lab(id)

--     CHECK (
--         workshop_id IS NOT NULL OR testlab_id IS NOT NULL
--     )
-- );

-- CREATE TABLE employee_logs (
--     id UUID PRIMARY KEY,
--     employee_id UUID REFERENCES employee(id),
--     position employee_position NOT NULL,
--     start_work TIMESTAMP NOT NULL,
--     end_work TIMESTAMP
-- );

-- -- CREATE TABLE users (
-- --     id UUID PRIMARY KEY,
-- --     role TEXT CHECK (role IN ('head', 'dephead', 'w_shop', 'head_company', 'test_lab_admin', 'accountant')),
-- --     login TEXT,
-- --     password_hash TEXT
-- -- );

-- -- про роли

-- -- Роли в системе
-- CREATE TABLE roles (
--     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     name TEXT UNIQUE NOT NULL
-- );

-- -- Пользователи системы
-- CREATE TABLE user_accounts (
--     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     username TEXT UNIQUE NOT NULL,
--     password_hash TEXT NOT NULL,
--     created_at TIMESTAMP DEFAULT now()
-- );

-- CREATE TABLE user_roles (
--     user_id UUID REFERENCES user_accounts(id) ON DELETE CASCADE,
--     role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
--     PRIMARY KEY (user_id, role_id)
-- );