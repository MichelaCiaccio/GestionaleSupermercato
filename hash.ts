/**
 * Since authentication is still not handled, I want to simulate at least user authentication.
 * And because I need passwords for it, I created this little script to hash a password for me.
 */
import bcrypt from 'bcryptjs';
import { exit } from 'process';

const hardcoded = '';
const password = process.argv[2] || hardcoded;
if (!password) {
  console.error(
    'Missing password. Usage: ts-node hash.ts your-password-here. You can also hardcode the password by opening this file and modifying the hardcoded variable to prevent the terminal from logging your inputs.'
  );
  exit();
}

const salt = bcrypt.genSaltSync();
const hash = bcrypt.hashSync(password, salt);

console.log(`Your hashed password is: ${hash}`);
