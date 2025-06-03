
-- Для генерации UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Head of department must be an engineers
CREATE FUNCTION check_department_head() RETURNS trigger AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM engineers WHERE employee_id = NEW.head) THEN
        RAISE EXCEPTION 'Head of department must be an engineers';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_department_head
BEFORE INSERT OR UPDATE ON department
FOR EACH ROW
EXECUTE FUNCTION check_department_head();

-- Head of workshop must be an engineers
CREATE FUNCTION check_workshop_head() RETURNS trigger AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM engineers WHERE employee_id = NEW.head) THEN
        RAISE EXCEPTION 'Head of workshop must be an engineers';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_workshop_head
BEFORE INSERT OR UPDATE ON workshop
FOR EACH ROW
EXECUTE FUNCTION check_workshop_head();

-- Head of brigade must be a worker
CREATE FUNCTION check_brigade_head() RETURNS trigger AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM workers WHERE employee_id = NEW.head) THEN
        RAISE EXCEPTION 'Head of brigade must be a worker';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_brigade_head
BEFORE INSERT OR UPDATE ON brigade
FOR EACH ROW
EXECUTE FUNCTION check_brigade_head();

-- Employee position check

CREATE OR REPLACE FUNCTION check_employee_position() RETURNS trigger AS $$
BEGIN
    IF TG_TABLE_NAME = 'workers' AND (SELECT position FROM employee WHERE id = NEW.employee_id) <> 'worker' THEN
        RAISE EXCEPTION 'Employee must have position ''worker'' to be added to workers';
    ELSIF TG_TABLE_NAME = 'engineers' AND (SELECT position FROM employee WHERE id = NEW.employee_id) <> 'engineer' THEN
        RAISE EXCEPTION 'Employee must have position ''engineer'' to be added to engineers';
    ELSIF TG_TABLE_NAME = 'testers' AND (SELECT position FROM employee WHERE id = NEW.employee_id) <> 'tester' THEN
        RAISE EXCEPTION 'Employee must have position ''tester'' to be added to testers';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_worker_position
BEFORE INSERT OR UPDATE ON workers
FOR EACH ROW
EXECUTE FUNCTION check_employee_position();

CREATE TRIGGER trg_check_engineers_position
BEFORE INSERT OR UPDATE ON engineers
FOR EACH ROW
EXECUTE FUNCTION check_employee_position();

CREATE TRIGGER trg_check_tester_position
BEFORE INSERT OR UPDATE ON testers
FOR EACH ROW
EXECUTE FUNCTION check_employee_position();


-- Delete employee cascade
CREATE FUNCTION delete_employee() RETURNS trigger AS $$
BEGIN
    DELETE FROM workers WHERE employee_id = OLD.id;
    DELETE FROM engineers WHERE employee_id = OLD.id;
    DELETE FROM testers WHERE employee_id = OLD.id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_delete_employee
AFTER DELETE ON employee
FOR EACH ROW
EXECUTE FUNCTION delete_employee();

-- Employee logs
CREATE OR REPLACE FUNCTION log_employee_start() RETURNS trigger AS $$
BEGIN
    INSERT INTO employee_logs (employee_id, position, start_work)
    VALUES (NEW.id, NEW.position, now());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_log_employee_start
AFTER INSERT ON employee
FOR EACH ROW
EXECUTE FUNCTION log_employee_start();

CREATE FUNCTION log_employee_end() RETURNS trigger AS $$
BEGIN
    UPDATE employee_logs
    SET end_work = now()
    WHERE employee_id = OLD.id AND end_work IS NULL;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_log_employee_end_on_delete
BEFORE DELETE ON employee
FOR EACH ROW
EXECUTE FUNCTION log_employee_end();

-- CREATE FUNCTION log_employee_position_change() RETURNS trigger AS $$
-- BEGIN
--     UPDATE employee_logs SET end_work = now()
--     WHERE employee_id = OLD.id AND end_work IS NULL;

--     INSERT INTO employee_logs (id, employee_id, position, start_work)
--     VALUES (gen_random_uuid(), NEW.id, NEW.position, now());
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION log_employee_position_change() RETURNS trigger AS $$
BEGIN
    UPDATE employee_logs
    SET end_work = now()
    WHERE employee_id = OLD.id AND end_work IS NULL;

    INSERT INTO employee_logs (employee_id, position, start_work)
    VALUES (NEW.id, NEW.position, now());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_log_employee_position_change
AFTER UPDATE OF position ON employee
FOR EACH ROW
WHEN (OLD.position IS DISTINCT FROM NEW.position)
EXECUTE FUNCTION log_employee_position_change();