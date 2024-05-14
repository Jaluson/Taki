import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

import Components from 'unplugin-vue-components/vite';
import {AntDesignVueResolver} from 'unplugin-vue-components/resolvers';
import path from 'path'


// https://vitejs.dev/config/
export default defineConfig({
    server: {
      port: 9421
    },
    plugins: [
        vue(),
        Components({
            resolvers: [
                AntDesignVueResolver({
                    importStyle: false, // css in js
                }),
            ],
        })
    ],
    resolve:{
        alias:{
            '@': path.join(__dirname, './src')
        }
    }
})
