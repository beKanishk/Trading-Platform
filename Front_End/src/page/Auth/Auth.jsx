import React from 'react'
import "./Auth.css"
import SignUpForm from './SignUpForm'
import { useLocation, useNavigate } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import ForgotPasswordForm from './ForgotPasswordForm'
import SIgnInForm from './SIgnInForm'

const Auth = () => {
    const navigate = useNavigate()
    const location = useLocation("")

  return (
    <div className="h-screen w-screen flex justify-center items-center relative authContainer bg-[#030712]">
      
      {/* Background Overlay */}
      <div className="absolute inset-0 bg-black bg-opacity-50"></div>

      {/* Centered Authentication Box */}
      <div className="relative flex flex-col justify-center items-center h-[35rem] w-[30rem] rounded-md bg-black bg-opacity-70 shadow-2xl shadow-white px-10">
        <h1 className="text-white pb-9 text-6xl font-semibold">Being</h1>

        {location.pathname == "/signup" ? <section className='w-full'>
            <SignUpForm/>
            <div className='flex items-center justify-center  mt-2'>
                <span>Already have a account ?</span>
                <Button onClick={()=>navigate("/signin")} variant="ghost">
                    signin
                </Button>
            </div>
        </section> : location.pathname == "/forgot-password" ? 
        <section className='w-full'>
            <ForgotPasswordForm/>
            <div className='flex items-center justify-center mt-2'>
                <span>back to login ?</span>
                <Button onClick={()=>navigate("/signin")} variant="ghost">
                    signin
                </Button>
            </div>
        </section> : 
        <section className='w-full'>
            <SIgnInForm/>
            <div className='flex items-center justify-center  mt-2'>
                <span>don't have a account ? </span>
                <Button onClick={()=>navigate("/signup")} variant="ghost">
                    signup
                </Button>
            </div>

            <div className='mt-10'>
                <Button 
                className={"w-full py-5"}
                onClick={()=>navigate("/forgot-password")} variant="outline">
                    Forgot Password
                </Button>
            </div>
        </section>}
      </div>

    </div>
  )
}

export default Auth
