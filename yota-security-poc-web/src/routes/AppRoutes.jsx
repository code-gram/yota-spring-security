import {Route, Routes} from "react-router-dom";
import {Login} from "../pages/login/Login";
import {SignUp} from "../pages/register/SignUp";
import {useEffect} from "react";
import axios from "axios";
import {isTokenExpired} from "../security/jwt/JwtService";
import {Home} from "../pages/home/Home";
import {getDecryption} from "../security/crypto/EncryptionDecryption";
import {TOKEN_KEY} from "../constants/helperConstants";
import {useDispatch} from "react-redux";
import {syncUserAuthData} from "../features/login/loginAction";

export const AppRoutes = () => {

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(syncUserAuthData());
    }, [dispatch]);

    useEffect(() => {
        const requestInterceptor = axios.interceptors.request.use(
            (config) => {
                const token = localStorage.getItem(TOKEN_KEY);
                if (token) {
                    const decryptedToken = getDecryption(token);
                    if (decryptedToken && !isTokenExpired(decryptedToken)) {
                        config.headers['Authorization'] = `Bearer ${decryptedToken}`;
                    }
                }
                config.headers['Content-Type'] = "application/json";
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );

        return () => {
            axios.interceptors.request.eject(requestInterceptor);
        };
    }, []);

    return (
        <Routes>
            <Route path={"/"} element={<Login/>}/>
            <Route path={"/login"} element={<Login/>}/>
            <Route path={"/register"} element={<SignUp/>}/>
            <Route path={"/home"} element={<Home/>}/>
        </Routes>
    )
};
