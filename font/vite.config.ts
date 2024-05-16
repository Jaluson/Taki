import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

import path from 'path'
import Components from 'unplugin-vue-components/vite';
import { NaiveUiResolver } from 'unplugin-vue-components/resolvers';
import AutoImport from 'unplugin-auto-import/vite';

export default defineConfig({
    server: {
        port: 9421
    },
    plugins: [
        vue(),
        Components({
            resolvers: [NaiveUiResolver()],
        }),
        AutoImport({
            resolvers: [NaiveUiResolver()],
        }),
    ],
    resolve: {
        alias: {
            '@': path.join(__dirname, './src')
        }
    }
})
