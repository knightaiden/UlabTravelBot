CREATE TABLE auth_config
(
  id    INTEGER primary key,
  partener_name varchar(150),
  client_key  varchar(100),
  secret_key varchar(100),
  token varchar(150),
  last_update DATETIME
);

