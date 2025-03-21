'use client';

import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { DarkModeToggle } from '../dark-mode-toggle';
import Link from 'next/link';
import { useActionState } from 'react';
import { authenticate, LoginFormState } from './actions';
import { useSearchParams } from 'next/navigation';
import { zodResolver } from '@hookform/resolvers/zod';
import { UserSchema, UserValues } from '@/lib/entities/user';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '../form';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';

const resolver = zodResolver(UserSchema);

const initialState: LoginFormState = {
  message: '',
  success: false,
  values: { email: '', password: '' },
};

const authenticateWrapped = async (
  prevState: LoginFormState,
  formData: FormData
) => {
  const result = await authenticate(prevState, formData);
  if (!result.success) {
    toast.warning(result.message);
  }
  return result;
};

export function LoginForm({
  className,
  ...props
}: React.ComponentPropsWithoutRef<'div'>) {
  const searchParams = useSearchParams();
  const callbackUrl = searchParams.get('callbackUrl') || '/dashboard';
  const [state, formAction, isPending] = useActionState(
    authenticateWrapped,
    initialState
  );

  const form = useForm<UserValues>({
    resolver,
    errors: state.errors,
    values: state.values,
  });

  return (
    <div className={cn('flex flex-col gap-6', className)} {...props}>
      <Card>
        <CardHeader className="flex flex-row justify-between gap-4">
          <div className="flex flex-col gap-1.5">
            <CardTitle className="text-2xl">Login</CardTitle>
            <CardDescription>
              Enter your email and password below to login to your account
            </CardDescription>
          </div>
          <DarkModeToggle />
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form action={formAction}>
              <div className="mb-6 flex flex-col gap-6">
                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Email</FormLabel>
                      <FormControl>
                        <Input
                          type="email"
                          placeholder="m@example.com"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="password"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Password</FormLabel>
                      <FormControl>
                        <Input type="password" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <input type="hidden" name="redirectTo" value={callbackUrl} />
              <Button
                type="submit"
                className="w-full cursor-pointer"
                disabled={isPending}
              >
                {isPending ? 'Logging in...' : 'Login'}
              </Button>

              <div className="mt-4 text-center text-sm">
                Do you have an operator code?{' '}
                <Link href="/signup" className="underline underline-offset-4">
                  Sign up
                </Link>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
