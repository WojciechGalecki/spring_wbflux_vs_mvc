import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    scenarios: {
        name1: {
            executor: 'shared-iterations',
            startTime: '10s',
            gracefulStop: '5s',
            tags: { tagName: 'tagValue' },
            vue: 100,
            iterations: 100,
            maxDuration: '10s'
        }
    }
};

export default function () {
    const res = http.get('http://localhost:8080/comments');

    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}
