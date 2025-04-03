import { useEffect } from 'react'
import { Route, Routes } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { getUser } from './State/Auth/Action'
import Navbar from './page/Navbar/Navbar'
import Home from './page/Home/Home'
import Portfolio from './page/Portfolio/Portfolio'
import { Activity } from './page/Activity/Activity'
import Wallet from './page/Wallet/Wallet'
import Withdrawal from './page/Withdrawal/Withdrawal'
import { PaymentDetails } from './page/Payment Details/PaymentDetails'
import StockDetails from './page/Stock/StockDetails'
import Watchlist from './page/Watchlist/Watchlist'
import { Profile } from './page/Profile/Profile'
import SearchCoin from './page/Search/SearchCoin'
import { NotFound } from './page/Not found/NotFound'
import Auth from './page/Auth/Auth'

function App() {
  const auth = useSelector((state) => state.auth); // ✅ Ensure auth is selected correctly
  const dispatch = useDispatch();

  console.log("auth:", auth);

  useEffect(() => {
    const jwt = auth?.jwt || localStorage.getItem("jwt");
    if (jwt) {
      dispatch(getUser(jwt));
    }
  }, [auth.jwt, dispatch]); // ✅ Depend on auth.jwt to re-run when it updates

  return (
    <>
      {auth?.user ? (
        <div>
          <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/portfolio" element={<Portfolio />} />
            <Route path="/activity" element={<Activity />} />
            <Route path="/wallet" element={<Wallet />} />
            <Route path="/withdrawal" element={<Withdrawal />} />
            <Route path="/payment-details" element={<PaymentDetails />} />
            <Route path="/market/:id" element={<StockDetails />} />
            <Route path="/watchlist" element={<Watchlist />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/search" element={<SearchCoin />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </div>
      ) : (
        <Auth />
      )}
    </>
  );
}

export default App;
