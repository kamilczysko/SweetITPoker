export default class StompClient {
    constructor (host) {
        const prefix = "http://localhost:8080"
        console.log(prefix + host)
        this.socket = new SockJS(prefix+""+host)
        this.client = Stomp.over(this.socket)
    }

    isConnected() {
        return this.socket != null && 
            (this.socket.readyState === 1 || this.socket.readyState === 0)
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