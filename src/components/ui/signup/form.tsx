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
import { Label } from '@/components/ui/label';
import { DarkModeToggle } from '../dark-mode-toggle';
import Link from 'next/link';

export function SignUpForm({
  className,
  ...props
}: React.ComponentPropsWithoutRef<'div'>) {
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
          <form>
            <div className="flex flex-col gap-6">
              <div className="grid gap-2">
                <Label htmlFor="op-code">Operator Code</Label>
                <Input
                  id="op-code"
                  type="text"
                  placeholder="e.g. ak923ihfaofdas"
                  required
                />
              </div>
              <Button type="submit" className="w-full">
                Sign Up
              </Button>
            </div>
            <div className="mt-4 text-center text-sm">
              Already registered?{' '}
              <Link href="/login" className="underline underline-offset-4">
                Login
              </Link>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
