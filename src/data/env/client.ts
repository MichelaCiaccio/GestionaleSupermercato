import { createEnv } from '@t3-oss/env-nextjs';
import { z } from 'zod';

export const env = createEnv({
  client: {
    NEXT_PUBLIC_NODE_ENV: z.enum(['development', 'production']),
    NEXT_PUBLIC_DB_BASE_URL: z.string().min(1),
  },
  experimental__runtimeEnv: {
    NEXT_PUBLIC_NODE_ENV: process.env.NEXT_PUBLIC_NODE_ENV,
    NEXT_PUBLIC_DB_BASE_URL: process.env.NEXT_PUBLIC_DB_BASE_URL,
  },
});
