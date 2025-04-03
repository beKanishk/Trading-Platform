import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import React, { useEffect, useState } from 'react'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Button } from '@/components/ui/button'
import PaymentDetailsForm from './PaymentDetailsForm'
import { useDispatch, useSelector } from 'react-redux'
import { getPaymentDetails } from '@/State/Withdrawal/Action'


export const PaymentDetails = () => {
  // const {withdrawal} = useSelector(store => store);
  // const dispatch = useDispatch();

  // useEffect(() => {
  //   dispatch(getPaymentDetails({jwt: localStorage.getItem("jwt")}))
  // }, [])
  const paymentDetails = JSON.parse(localStorage.getItem("paymentDetails"));
  return (
    <div className='px-20'>
      <h1 className='text-3xl font-bold py-10 text-left'>Payment Details</h1>

      {paymentDetails ? 
      <Card className="text-left">
        <CardHeader>
          <CardTitle>
          {paymentDetails.bankName}
          </CardTitle>
          <CardDescription>
          A/C No : 
          {paymentDetails?.accountNumber
            ? `${"*".repeat(paymentDetails.accountNumber.length - 4)}${paymentDetails.accountNumber.slice(-4)}`
            : "N/A"}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className='flex items-center'>
            <p className='w-32'>A/C Holder</p>
            <p className='text-gray-400'>{paymentDetails.accountHolderName}</p>
          </div>

          <div className='flex items-center'>
            <p className='w-32'>IFSC</p>
            <p className='text-gray-400'>{paymentDetails.ifsc}</p>
          </div>
        </CardContent>
      </Card> :
      <div className="flex justify-start pt-4">
      <Dialog>
        <DialogTrigger>
          <Button className="py-6">Add payment details</Button>
        </DialogTrigger>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Payment Details</DialogTitle>
          </DialogHeader>
          <PaymentDetailsForm />
        </DialogContent>
      </Dialog>
    </div>}

      


    </div>
  )
}
