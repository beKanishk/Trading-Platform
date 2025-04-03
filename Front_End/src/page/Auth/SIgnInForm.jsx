import { Form, FormControl, FormField, FormItem, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { useDispatch } from 'react-redux';
import { login } from '@/State/Auth/Action';
import { useNavigate } from 'react-router-dom';

// Define schema using zod
const formSchema = z.object({
    email: z.string().email("Enter a valid email address"),
    password: z.string().min(6, "Password must be at least 6 characters"),
});

const SignInForm = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate()
    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            email: "",
            password: ""
        }
    });

    const onSubmit = (data) => {
        dispatch(login({data, navigate}));
        console.log("Login Data:", data);
    };

    return (
        <div>
            <h1 className="text-xl font-bold text-center pb-3">Login</h1>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                    
                    <FormField
                        control={form.control}
                        name="email"
                        render={({ field }) => (
                            <FormItem>
                                <FormControl>
                                    <Input 
                                        className="border w-full border-gray-700 p-5" 
                                        placeholder="Enter your email" 
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
                                <FormControl>
                                    <Input 
                                        type="password"
                                        className="border w-full border-gray-700 p-5" 
                                        placeholder="Enter your password" 
                                        {...field} 
                                    />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <Button type="submit" className="w-full py-5">
                        Submit
                    </Button>
                </form>
            </Form>
        </div>
    );
};

export default SignInForm;
