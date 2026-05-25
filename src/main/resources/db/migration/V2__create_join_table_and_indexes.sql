-- V2: Tabla intermedia Many-to-Many e índices de rendimiento

CREATE TABLE IF NOT EXISTS books_genres (
                              book_id BIGINT NOT NULL,
                              genre_id BIGINT NOT NULL,
                              PRIMARY KEY (book_id, genre_id),
                              CONSTRAINT fk_bg_book FOREIGN KEY (book_id) REFERENCES books(id),
                              CONSTRAINT fk_bg_genre FOREIGN KEY (genre_id) REFERENCES genres(id)
);

-- Índices para optimizar consultas frecuentes
CREATE INDEX IF NOT EXISTS idx_books_publication_date ON books(publication_date);
CREATE INDEX IF NOT EXISTS idx_books_available ON books(available);
CREATE INDEX IF NOT EXISTS idx_books_editorial ON books(editorial_id);
CREATE INDEX IF NOT EXISTS idx_editorials_country ON editorials(country);
