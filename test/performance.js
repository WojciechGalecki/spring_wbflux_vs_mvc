import http from 'k6/http';
import { sleep, check } from 'k6';

// k6 run --vus <vus> --duration <duration> performance.js
export const options = {
    vus: 100,
    duration: '10s'
};

export default function () {
    const res = http.get('http://localhost:8080/comments');

    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}

// k6 run performance.js
// k6 login cloud --token <YOUR_TOKEN>
