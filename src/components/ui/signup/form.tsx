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
import { signup } from './actions';
import { Form, FormField, FormItem, FormLabel } from '../form';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';

const signupWrapped: typeof signup = async () => {
  const result = await signup();
  toast.warning(result);
  return result;
};

export function SignUpForm({
  className,
  ...props
}: React.ComponentPropsWithoutRef<'div'>) {
  const formAction = useActionState(signupWrapped, undefined)[1];
  const form = useForm();

  return (
    <div className={cn('flex flex-col gap-6', className)} {...props}>
      <Card>
        <CardHeader className="flex flex-row justify-between gap-4">
          <div className="flex flex-col gap-1.5">
            <CardTitle className="text-2xl">Sign Up</CardTitle>
            <CardDescription>
              Enter your operator code to register your new account!
            </CardDescription>
          </div>
          <DarkModeToggle />
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form action={formAction}>
              <FormField
                control={form.control}
                name="opcode"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Operator Code</FormLabel>
                    <Input
                      type="text"
                      placeholder="e.g. 728930040aa476f94153be3c561ab548"
                      {...field}
                    />
                  </FormItem>
                )}
              />

              <Button type="submit" className="mt-6 w-full cursor-pointer">
                Sign Up
              </Button>

              <div className="mt-4 text-center text-sm">
                Already registered?{' '}
                <Link href="/login" className="underline underline-offset-4">
                  Login
                </Link>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
