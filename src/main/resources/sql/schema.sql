CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE [IF NOT EXISTS] EXAMPLE (
   ID UUID(length) PRIMARY KEY UNIQUE NOT NULL DEFAULT uuid_generate_v4(),
   DESCRIPTION VARCHAR(255)
);
