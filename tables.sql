CREATE TYPE employee_position AS ENUM('worker', 'engineer', 'tester');

CREATE TABLE company (
    id UUID PRIMARY KEY,
    name TEXT
);

CREATE TABLE employee (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    position employee_position NOT NULL
);

CREATE TABLE department (
    id UUID PRIMARY KEY,
    head UUID REFERENCES employee(id),
    name TEXT,
    company_id UUID REFERENCES company(id)
);

CREATE TABLE test_lab (
    id UUID PRIMARY KEY,
    title TEXT
);

CREATE TABLE test_lab_equipment (
    id UUID PRIMARY KEY,
    title TEXT,
    test_lab_id UUID REFERENCES test_lab(id)
);

CREATE TABLE tlab_dep (
    test_lab_id UUID REFERENCES test_lab(id),
    department_id UUID REFERENCES department(id),
    PRIMARY KEY (test_lab_id, department_id)
);

CREATE TABLE product_category (
    id UUID PRIMARY KEY,
    title TEXT,
    attr JSON
);

CREATE TABLE product_model (
    id UUID PRIMARY KEY,
    title TEXT,
    category_id UUID REFERENCES product_category(id),
    department_id UUID REFERENCES department(id)
);

CREATE TABLE product (
    id UUID PRIMARY KEY,
    model_id UUID REFERENCES product_model(id)
);

CREATE TABLE workshop (
    id UUID PRIMARY KEY,
    department_id UUID REFERENCES department(id),
    name TEXT,
    head UUID REFERENCES employee(id)
);

CREATE TABLE brigade (
    id UUID PRIMARY KEY,
    head UUID REFERENCES employee(id),
    workshop_id UUID REFERENCES workshop(id)
);

CREATE TABLE workers (
    id UUID PRIMARY KEY,
    employee_id UUID REFERENCES employee(id),
    brigade_id UUID REFERENCES brigade(id)
);

CREATE TABLE engineers (
    id UUID PRIMARY KEY,
    employee_id UUID REFERENCES employee(id),
    workshop_id UUID REFERENCES workshop(id)
);

CREATE TABLE testers (
    id UUID PRIMARY KEY,
    employee_id UUID REFERENCES employee(id),
    test_lab_id UUID REFERENCES test_lab(id)
);

CREATE TABLE product_logs (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES product(id),
    -- status TEXT CHECK (status IN ('done', 'in_progress', 'test')),
    start_work TIMESTAMP NOT NULL,
    end_work TIMESTAMP,
    workshop_id UUID REFERENCES workshop(id),
    testlab_id UUID REFERENCES test_lab(id)

    CHECK (
        workshop_id IS NOT NULL OR testlab_id IS NOT NULL
    )
);

CREATE TABLE employee_logs (
    id UUID PRIMARY KEY,
    employee_id UUID REFERENCES employee(id),
    position employee_position NOT NULL,
    start_work TIMESTAMP NOT NULL,
    end_work TIMESTAMP
);

-- CREATE TABLE users (
--     id UUID PRIMARY KEY,
--     role TEXT CHECK (role IN ('head', 'dephead', 'w_shop', 'head_company', 'test_lab_admin', 'accountant')),
--     login TEXT,
--     password_hash TEXT
-- );

-- про роли

-- Роли в системе
CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT UNIQUE NOT NULL
);

-- Пользователи системы
CREATE TABLE user_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,  -- можно заменить на авторизацию извне
    created_at TIMESTAMP DEFAULT now()
);

-- Привязка пользователей к ролям (многие ко многим)
CREATE TABLE user_roles (
    user_id UUID REFERENCES user_accounts(id) ON DELETE CASCADE,
    role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


-- Представление для ограниченного доступа к employee_logs
CREATE VIEW employee_logs_admin_view AS
SELECT * FROM employee_logs
WHERE EXISTS (
    SELECT 1 FROM user_accounts ua
    JOIN user_roles ur ON ua.id = ur.user_id
    JOIN roles r ON ur.role_id = r.id
    WHERE r.name = 'admin' AND ua.username = current_user
);
