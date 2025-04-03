import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import React from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"
import { DialogClose } from '@/components/ui/dialog'

// Define schema using zod
const formSchema = z.object({
    accountHolderName: z.string().min(2, "Name must be at least 2 characters"),
    ifsc: z.string().min(5, "Enter a valid IFSC code"),
    accountNumber: z.string().min(6, "Enter a valid account number"),
    bankName: z.string().min(2, "Bank name is required")
});

const ForgotPasswordForm = () => {
    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            email: "",
        }
    });

    const onSubmit = (data) => {
        console.log(data);
    }

    return (
        <div >
             <h1 className='text-xl font-bold text-center pb-3'>Forgot Password</h1>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className='space-y-6'>
                    {/* Account Holder Name */}
                    
                    
                    <FormField
                        control={form.control}
                        name="email"
                        render={({ field }) => (
                            <FormItem>
                                
                                <FormControl>
                                    <Input className="border w-full border-gray-700 p-5" placeholder="enter your email" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                

                    

                    {/* Submit Button */}
                    
                        <Button type="submit" className="w-full py-5">
                            Submit
                        </Button>
                    
                    
                </form>
            </Form>
        </div>
    );
}

export default ForgotPasswordForm;
