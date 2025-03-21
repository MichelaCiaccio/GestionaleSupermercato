import 'next-auth/jwt';
import type { DefaultSession, NextAuthConfig } from 'next-auth';
import { User as IUser } from './types/entities/user';

type AuthUser = Omit<IUser, 'password'>;

declare module 'next-auth' {
  // eslint-disable-next-line @typescript-eslint/no-empty-object-type
  interface User extends AuthUser {}

  interface Session {
    user: AuthUser & DefaultSession['user'];
  }
}

declare module 'next-auth/jwt' {
  interface JWT {
    user?: AuthUser;
  }
}

export const authConfig = {
  pages: {
    signIn: '/login',
  },
  callbacks: {
    authorized({ auth, request: { nextUrl } }) {
      const isLoggedIn = !!auth?.user;
      const isOnDashboard = nextUrl.pathname.startsWith('/dashboard');
      if (isOnDashboard) {
        if (isLoggedIn) return true;
        return false; // Redirect unauthenticated users to login page
      } else if (isLoggedIn) {
        return Response.redirect(new URL('/dashboard', nextUrl));
      }
      return true;
    },
    jwt({ token, user }) {
      if (user) {
        delete (user as Partial<IUser>).password;
        token.user = user as typeof token.user;
      }
      return token;
    },
    session({ session, token }) {
      if (token.user) {
        Object.assign(session.user, token.user);
      }
      return session;
    },
  },
  providers: [], // Add providers with an empty array for now
} satisfies NextAuthConfig;
