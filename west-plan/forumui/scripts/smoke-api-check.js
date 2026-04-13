/* eslint-disable no-console */
const baseURL = process.env.SMOKE_BASE_URL || process.argv[2] || 'http://127.0.0.1:8080'
const token = process.env.SMOKE_TOKEN || process.argv[3] || ''

const TIMEOUT_MS = 12000

const checks = [
    { name: 'captcha', method: 'get', url: '/captchaImage', auth: false },
    { name: 'getInfo', method: 'get', url: '/getInfo', auth: true },
    { name: 'forumList', method: 'get', url: '/system/forum_post/list?pageNum=1&pageSize=5', auth: true },
    { name: 'questionList', method: 'get', url: '/system/question_bank/list?pageNum=1&pageSize=5', auth: true },
    { name: 'messageMy', method: 'get', url: '/system/message/my', auth: true }
]

async function run() {
    let failed = 0
    console.log(`[smoke] baseURL=${baseURL}`)

    for (const item of checks) {
        const headers = {}
        if (item.auth) {
            if (!token) {
                console.log(`[skip] ${item.name} 需要 token，已跳过`)
                continue
            }
            headers.Authorization = `Bearer ${token}`
        }

        try {
            const controller = new AbortController()
            const timeoutId = setTimeout(() => controller.abort(), TIMEOUT_MS)
            const res = await fetch(`${baseURL}${item.url}`, {
                method: item.method.toUpperCase(),
                headers,
                signal: controller.signal
            })
            clearTimeout(timeoutId)
            const ok = res.status >= 200 && res.status < 300
            if (!ok) {
                failed += 1
            }
            console.log(`[${ok ? 'ok' : 'fail'}] ${item.name} ${res.status}`)
        } catch (err) {
            failed += 1
            const status = err && err.name === 'AbortError' ? 'TIMEOUT' : 'ERR'
            console.log(`[fail] ${item.name} ${status}`)
        }
    }

    if (failed > 0) {
        console.log(`[smoke] 失败 ${failed} 项`)
        process.exit(1)
    }
    console.log('[smoke] 全部通过')
}

run()
