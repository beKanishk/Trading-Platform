import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group'
import { paymentHandler } from '@/State/Wallet/Action'
import { CircleDot } from 'lucide-react'
import React, { useState } from 'react'
import { useDispatch } from 'react-redux'

const TopupForm = () => {
    const [amount, setAmount] = useState('')
    const [paymentMethod, setPaymentMethod] = useState("RAZORPAY")
    const dispatch = useDispatch()

    const handleSubmit = () => {
        console.log(amount, paymentMethod);
        dispatch(paymentHandler({jwt: localStorage.getItem("jwt"),
            paymentMethod,
            amount
        }))
    }
    const handlePaymentMethodChange = (value)=>{
        setPaymentMethod(value)
    }

    const handleChange = (e) =>{
        setAmount(e.target.value)
    }

  return (
    <div className='pt-10 space-y-5'>
        <div>
            <h1 className='pb-1'>Enter Amount</h1>
            <Input
            onChange={handleChange}
            value={amount}
            className="py-7 text-lg"
            placeholder="$9999"/>
        </div>
        <div>
            <h1 className='pb-1'>Select Payment Method</h1>
            <RadioGroup 
            className="flex" 
            onValueChange={(value)=>handlePaymentMethodChange(value)}
            defaultValue="RAZORPAY">
                <div className='flex items-center space-x-2 border p-3 px-5
                rounded-md'>
                    <RadioGroupItem icon={CircleDot}
                    className="h-9 w-9"
                    value="RAZORPAY"
                    id="r1"/>
                    <Label htmlFor="r1">
                        <div className='bg-white rounded-md px-5 w-32'>
                            <img className="h-9" src="" alt="razorpayimage" />
                        </div>
                    </Label>
                </div>

                <div className='flex items-center space-x-2 border p-3 px-5
                rounded-md'>
                    <RadioGroupItem icon={CircleDot}
                    className="h-9 w-9"
                    value="STRIPE"
                    id="r2"/>
                    <Label htmlFor="r2">
                        <div className='bg-white rounded-md px-5 w-32'>
                            <img className="h-9" src="" alt="stripeimage" />
                        </div>
                    </Label>
                </div>


            </RadioGroup>
        </div>
        <div>
        <Button onClick={handleSubmit} className="w-full py-7">
            Submit
        </Button>
        </div>
        
    </div>
  )
}

export default TopupForm