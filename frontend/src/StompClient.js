export default class StompClient {
    constructor (host) {
        const prefix = "http://localhost:8080"
        const socket = new SockJS(prefix + host)
        this.client = Stomp.over(socket)
    }

    subscribe(listenPath, callback) {
        this.client.connect({}, () => {
            this.client.subscribe(listenPath, callback)
        })
    }

    bulkSubscribe(pathToCallbackMap) {
        this.client.connect({}, () => {
            Array.from(pathToCallbackMap)
            .forEach(a => this.client.subscribe(a.path, a.callback))
        })
    }

    send(sendPath, message) {
        this.client.send(sendPath, {}, message)
    }

    disconnect() {
        this.client.disconnect()
    }
}