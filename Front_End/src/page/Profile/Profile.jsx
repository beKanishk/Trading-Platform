import { Badge } from '@/components/ui/badge'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { VerifiedIcon } from 'lucide-react'
import React from 'react'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Button } from '@/components/ui/button'
import AccountVerificationForm from './AccountVerificationForm'
import { useSelector } from 'react-redux'


export const Profile = () => {
  const auth = useSelector((state) => state.auth);

  const handleEnableTwoStepVerification = () =>{
    console.log("handleEnableTwoStepVerification")
  }

  return (
    <div className='flex flex-col items-center mb-5'>
      <div className='pt-10 w-full lg:w-[60%]'>
        <Card>
          <CardHeader className="px-6 pb-3">
            <CardTitle className="text-left text-xl">Your Information</CardTitle>
          </CardHeader>
          <CardContent className="px-6">
            {/* Two-column layout */}
            <div className="flex justify-between">
              
              {/* Left Column */}
              <div className='w-1/2 space-y-5'>
                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Email :</p>
                  <p className='text-gray-500 text-left'>{auth.user?.email}</p>
                </div>

                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Full Name :</p>
                  <p className='text-gray-500 text-left'>{auth.user?.name}</p>
                </div>

                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Date of Birth :</p>
                  <p className='text-gray-500 text-left'>16/09/2003</p>
                </div>

                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Nationality :</p>
                  <p className='text-gray-500 text-left'>Indian</p>
                </div>
              </div>

              {/* Right Column */}
              <div className='w-1/2 space-y-5'>
                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Phone :</p>
                  <p className='text-gray-500 text-left'>+91 9876543210</p>
                </div>

                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Address :</p>
                  <p className='text-gray-500 text-left'>Pune, India</p>
                </div>

                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Gender :</p>
                  <p className='text-gray-500 text-left'>Female</p>
                </div>

                <div className='flex items-center space-x-5'> 
                  <p className='w-[9rem] font-medium text-gray-200 text-left'>Occupation :</p>
                  <p className='text-gray-500 text-left'>Software Engineer</p>
                </div>
              </div>

            </div>
          </CardContent>
        </Card>

        <div className='mt-6'>
          <Card className="w-full">
            <CardHeader className="pb-7">
              <div className='flex items-center gap-3'>
                <CardTitle>2 Step Verification</CardTitle>
                {true?<Badge className={"space-x-2 text-white bg-green-600"}>
                  <VerifiedIcon/>
                  <span>Enabled</span>
                </Badge> : <Badge className="bg-orange-500">Disabled</Badge>}

                
                
              </div>
            </CardHeader>
            <CardContent>
              <div>
                <Dialog>
                  <DialogTrigger>
                    <Button>Enable Two Step Verification</Button>
                  </DialogTrigger>
                  <DialogContent>
                    <DialogHeader>
                      <DialogTitle>Verify your account</DialogTitle>
                      
                    </DialogHeader>
                    <AccountVerificationForm handleSubmit={handleEnableTwoStepVerification}/>
                  </DialogContent>
                </Dialog>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  )
}
