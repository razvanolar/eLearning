UPDATE cursuri SET enrolment_key = '' WHERE enrolment_key IS NULL;
ALTER TABLE cursuri MODIFY enrolment_key VARCHAR(255) NOT NULL DEFAULT '';
ALTER TABLE studenti_inscrisi ADD UNIQUE (ref_student, ref_curs);