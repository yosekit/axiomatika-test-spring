-- Employment Table --
CREATE TABLE "employment"(
    "id" serial PRIMARY KEY,
    "post" varchar(255) NOT NULL,
    "company_name" varchar(255) NOT NULL,
    "salary" bigint NOT NULL,
    "start_date" date NOT NULL
);


-- Passport Table --
CREATE TABLE "passport"(
    "id" serial PRIMARY KEY,
    "series" char(4) NOT NULL,
    "number" char(6) NOT NULL,
    "issuance_date" date NOT NULL,
    "subdivision_code" char(7) NOT NULL,
    "issued" varchar(255) NOT NULL
);


-- Client Table --
CREATE TYPE "family_status" AS ENUM ('SINGLE', 'MARRIED');

CREATE TABLE "client"(
    "id" serial PRIMARY KEY,
    "full_name" varchar(255) NOT NULL,
    "birthdate" date NOT NULL,
    "phone" varchar(255) NOT NULL,
    "family_status" family_status,
    "employment_id" integer UNIQUE,
    "passport_id" integer UNIQUE,
    FOREIGN KEY ("employment_id") REFERENCES "employment"("id"),
    FOREIGN KEY ("passport_id") REFERENCES "passport"("id")
);


-- Address Table --
CREATE TYPE "address_type" AS ENUM ('RESIDENCE', 'REGISTRATION');

CREATE TABLE "address"(
    "id" serial PRIMARY KEY,
    "string" varchar(255) NOT NULL,
    "type" address_type,
    "client_id" integer NOT NULL,
    FOREIGN KEY ("client_id") REFERENCES "client"("id")
);


-- Application Table --
CREATE TYPE "application_status" AS ENUM ('OPEN', 'REJECTED', 'APPROVED');

CREATE TABLE "application"(
    "id" serial PRIMARY KEY,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "client_id" integer NOT NULL,
    "required_amount" bigint NOT NULL,
    "required_term" date NOT NULL,
    "status" application_status DEFAULT 'OPEN'
);


-- Contract Table --
CREATE TYPE "contract_sign_status" AS ENUM ('UNSIGNED', 'SIGNED');

CREATE TABLE "contract"(
    "id" serial PRIMARY KEY,
    "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
    "application_id" integer UNIQUE,
    "amount" bigint NOT NULL,
    "term" date NOT NULL,
    "sign_date" date,
    "sign_status" contract_sign_status DEFAULT 'UNSIGNED',
    FOREIGN KEY ("application_id") REFERENCES "application"("id")
);



