import * as dotenv from 'dotenv';

dotenv.config();

/**
 * Centralized environment configuration.
 * Values are read from `.env` (see `.env.example`) with sane fallbacks
 * pointing at the public CRM demo used for this course.
 */
export const env = {
  baseUrl: process.env.BASE_URL ?? 'https://crm.anhtester.com',
  validEmail: process.env.VALID_EMAIL ?? 'admin@example.com',
  validPassword: process.env.VALID_PASSWORD ?? '123456',
};
