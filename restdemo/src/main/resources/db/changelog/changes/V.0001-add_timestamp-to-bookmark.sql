alter table bookmark
  add column created_at timestamp not null default now(),
  add column update_at timestamp not null default now();