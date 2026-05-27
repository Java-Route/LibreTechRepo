-- V4: Additional generated books for local catalog testing

INSERT INTO books (title, author, isbn, publication_date, price, available, editorial_id)
SELECT
    'Libro ' || i AS title,
    'Autor ' || ((i % 50) + 1) AS author,
    'ISBN-' || LPAD(i::text, 6, '0') AS isbn,
    DATE '2000-01-01' + (i || ' days')::interval AS publication_date,
    10000 + (i % 100) * 750 AS price,
    (i % 2 = 0) AS available,
    (i % 10) + 1 AS editorial_id
FROM generate_series(21, 520) AS i
ON CONFLICT (isbn) DO NOTHING;

INSERT INTO books_genres (book_id, genre_id)
SELECT
    b.id AS book_id,
    ((i % 7) + 1) AS genre_id
FROM generate_series(21, 520) AS i
JOIN books b ON b.isbn = 'ISBN-' || LPAD(i::text, 6, '0')
ON CONFLICT DO NOTHING;
