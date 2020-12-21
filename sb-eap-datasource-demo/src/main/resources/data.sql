insert into book values (1, 'Linux book', 'Shadowman')
    ON CONFLICT ON CONSTRAINT book_pkey
    DO NOTHING;
insert into book values (2, 'java book', 'Duke')
    ON CONFLICT ON CONSTRAINT book_pkey
    DO NOTHING;
insert into book values (3, 'go book', 'Gopher')
    ON CONFLICT ON CONSTRAINT book_pkey
    DO NOTHING;