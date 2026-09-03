import { env } from '../config/env';

/** Credentials and expected messages used across login tests. */
export const loginData = {
  valid: {
    email: env.validEmail,
    password: env.validPassword,
  },
  invalid: {
    wrongPassword: {
      email: env.validEmail,
      password: 'wrong-password-123',
    },
    unregisteredEmail: {
      email: 'not-registered@example.com',
      password: '123456',
    },
  },
  messages: {
    invalidCredentials: 'Invalid email or password',
  },
};
