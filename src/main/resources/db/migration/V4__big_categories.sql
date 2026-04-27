create table big_categories (
    id bigserial primary key,
    name varchar(255) not null unique
);

alter table categories
add column big_category_id bigint;

alter table categories
add constraint fk_category_big_category
foreign key (big_category_id)
references big_categories(id);