CREATE USER metrikano_user WITH PASSWORD 'metrikano_pass';
CREATE USER metrikano_auth_user WITH PASSWORD 'metrikano_auth_pass';

CREATE DATABASE metrikano_db;
GRANT ALL PRIVILEGES ON DATABASE metrikano_db TO metrikano_user;

CREATE DATABASE metrikano_auth_db;
GRANT ALL PRIVILEGES ON DATABASE metrikano_auth_db TO metrikano_auth_user;