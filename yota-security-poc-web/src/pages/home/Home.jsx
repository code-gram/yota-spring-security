import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {TOKEN_KEY} from "../../constants/helperConstants";
import {isTokenExpired} from "../../security/jwt/JwtService";
import {useDispatch, useSelector} from "react-redux";
import {logout} from "../../features/login/loginAction";

export const Home = () => {

    const navigate = useNavigate();
    const userData = useSelector((state) => state.auth.userData);
    const dispatch = useDispatch();
    const role = userData.userRole?.substring(5).replace('_', ' ');

    useEffect(() => {
        const token = localStorage.getItem(TOKEN_KEY);
        if (token) {
            if (isTokenExpired(token)) {
                console.log('Token expired')
                logoutHandler();
            }
        } else {
            console.log('token not found')
            navigate('/');
        }
    }, [navigate]);

    const logoutHandler = () => {
        dispatch(logout());
        navigate("/");
    };

    return (
        <div>
            <h2>Home Page</h2>
            {userData && (
                <div>
                    <h3>Welcome, {userData.fullName}</h3>
                    <br/>
                    <h4>This is the home page for {role}</h4>
                </div>
            )}

            <div>
                <button className={"btn btn-danger"} onClick={logoutHandler}>Logout</button>
            </div>
        </div>
    )
};
