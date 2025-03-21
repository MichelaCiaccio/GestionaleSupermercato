import NextAuth from 'next-auth';
import { authConfig } from './auth.config';
import Credentials from 'next-auth/providers/credentials';
import { UserSchema } from '@/lib/entities/user';
import db from '@/lib/db';
import bcrypt from 'bcryptjs';

export const { auth, signIn, signOut } = NextAuth({
  ...authConfig,
  providers: [
    Credentials({
      async authorize(credentials) {
        const parsedCredentials = UserSchema.safeParse(credentials);

        if (!parsedCredentials.success) return null;

        const { email, password } = parsedCredentials.data;
        const user = await db.users.getByEmail(email);

        if (!user) return null;

        const passwordsMatched = await bcrypt.compare(password, user.password);
        if (!passwordsMatched) return null;

        return user;
      },
    }),
  ],
});
