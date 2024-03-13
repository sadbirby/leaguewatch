import React from 'react'
import { Navigate, Route, Routes } from 'react-router-dom'
import NavbarPreLogin from './components/navbar/NavbarPreLogin'
import NavbarPostLogin from './components/navbar/NavbarPostLogin'
import Home from './components/home/Home'
import Login from './components/login/Login'
import Register from './components/register/Register'
import Dashboard from './components/dashboard/Dashboard'
import UserProfile from './components/user/UserProfile'
import Leagues from './components/leagues/Leagues'
import Footer from './components/footer/Footer'


const App = () => {

  const [isAuthenticated, setIsAuthenticated] = React.useState(false);
  const isUserAuthenticated = localStorage.getItem("isAuthenticated");
  React.useEffect(() => {
    if (isUserAuthenticated == "true") {
      setIsAuthenticated(true);
    } else {
      setIsAuthenticated(false);
    }
  }, [isUserAuthenticated])

  return (isAuthenticated ?
    <React.Fragment>
      <div className="content-wrapper">
        {/* Navbar / Header */}
        < NavbarPostLogin />
        {/* Routing defined for post-login components */}
        <Routes>
          <Route path="/" element={<Navigate to="../dashboard" />} />
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="leagues" element={<Leagues />} />
          <Route path="profile" element={<UserProfile />} />
        </Routes>
      </div>
      <Footer />
    </React.Fragment>
    :
    <React.Fragment>
      <div className="content-wrapper">
        {/* Navbar / Header */}
        <NavbarPreLogin />
        {/* Routing defined for pre-login components */}
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />
        </Routes>
      </div>
      <Footer />
    </React.Fragment>
  );
}

export default App
