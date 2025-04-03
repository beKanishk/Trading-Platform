import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import React from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"
import { DialogClose } from '@/components/ui/dialog'
import { useDispatch } from 'react-redux'
import { addPaymentDetails } from '@/State/Withdrawal/Action'

// Define schema using zod
const formSchema = z.object({
    accountHolderName: z.string().min(2, "Name must be at least 2 characters"),
    ifsc: z.string().min(5, "Enter a valid IFSC code"),
    accountNumber: z.string().min(6, "Enter a valid account number"),
    bankName: z.string().min(2, "Bank name is required")
});

const PaymentDetailsForm = () => {
    const dispatch = useDispatch()
    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            accountHolderName: "",
            ifsc: "",
            accountNumber: "",
            bankName: ""
        }
    });

    const onSubmit = (data) => {
        dispatch(addPaymentDetails({
            paymentDetails: data,
            jwt: localStorage.getItem("jwt")
        }));
        console.log(data);
    }

    return (
        <div className='px-10 py-2'>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className='space-y-6'>
                    {/* Account Holder Name */}
                    <FormField
                        control={form.control}
                        name="accountHolderName"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Account Holder Name</FormLabel>
                                <FormControl>
                                    <Input className="border w-full border-gray-700 p-5" placeholder="Jhon Doe" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    
                    <FormField
                        control={form.control}
                        name="ifsc"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>IFSC Code</FormLabel>
                                <FormControl>
                                    <Input className="border w-full border-gray-700 p-5" placeholder="SBIN0006598" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="accountNumber"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Account Number</FormLabel>
                                <FormControl>
                                    <Input className="border w-full border-gray-700 p-5" placeholder="***********1313" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="confirmAccountNumber"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Confirm Account Number</FormLabel>
                                <FormControl>
                                    <Input className="border w-full border-gray-700 p-5" placeholder="***********1313" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="bankName"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Bank Name</FormLabel>
                                <FormControl>
                                    <Input className="border w-full border-gray-700 p-5" placeholder="Yes Bank" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    {/* Submit Button */}
                    <DialogClose className="w-full">
                        <Button type="submit" className="w-full py-5">
                            Submit
                        </Button>
                    </DialogClose>
                    
                </form>
            </Form>
        </div>
    );
}

export default PaymentDetailsForm;
