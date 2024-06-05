import {createRouter, createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "Login",
            component: () => import('@/view/user/login.vue'),
        },
        {
            path: "/register",
            name: "Register",
            component: () => import('@/view/user/register.vue')
        }
    ]

})

export default router
