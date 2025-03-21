'use server';

import { signIn, signOut } from '@/auth';
import { UserSchema, UserValues } from '@/lib/entities/user';
import { RawFormData } from '@/types/utils';
import { AuthError } from 'next-auth';
import { isRedirectError } from 'next/dist/client/components/redirect-error';
import { ZodError } from 'zod';

type FormError = Record<string, { message: string }>;

export type LoginFormState = {
  message: string;
  values: UserValues;
  errors?: FormError;
  success: boolean;
};

function getRawLoginFormData(formData: FormData): RawFormData<UserValues> {
  return {
    email: formData.get('email'),
    password: formData.get('password'),
  } as RawFormData<UserValues>;
}

function parseFormErrors(error: ZodError): FormError {
  const errors: FormError = {};
  for (const { path, message } of error.issues || []) {
    errors[path.join('.')] = { message };
  }
  return errors;
}

function buildLoginFormErrorObject(
  message: string,
  rawFormData: RawFormData<UserValues>,
  error: ZodError
): LoginFormState {
  return {
    errors: parseFormErrors(error),
    success: false,
    message,
    values: {
      email: rawFormData.email ?? '',
      password: rawFormData.password ?? '',
    },
  };
}

export async function authenticate(
  prevState: LoginFormState,
  formData: FormData
): Promise<LoginFormState> {
  const rawFormData = getRawLoginFormData(formData);
  const validatedFields = UserSchema.safeParse(rawFormData);
  if (!validatedFields.success) {
    return buildLoginFormErrorObject(
      'Missing or invalid fields.',
      rawFormData,
      validatedFields.error
    );
  }

  let message = '';
  try {
    await signIn('credentials', formData);
  } catch (error) {
    if (error instanceof AuthError) {
      switch (error.type) {
        case 'CredentialsSignin':
          message = 'Invalid credentials.';
          break;
        default:
          message = 'Something went wrong.';
      }
    } else if (isRedirectError(error)) {
      // Doesn't redirect on logged in unless error is thrown.
      // Check: https://github.com/nextauthjs/next-auth/discussions/9389
      throw error;
    } else {
      message = 'Something went wrong.';
    }
    console.error(error);
  }

  return {
    message,
    success: false,
    values: validatedFields.data,
  };
}

export async function logout() {
  await signOut({ redirectTo: '/login' });
}
