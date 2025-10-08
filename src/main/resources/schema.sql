CREATE SCHEMA IF NOT EXISTS user_svc;


CREATE UNIQUE INDEX IF NOT EXISTS uq_users_email_lower
    ON user_svc.users (lower(email));