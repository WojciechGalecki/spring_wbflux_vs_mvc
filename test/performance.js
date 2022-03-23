import { check } from "k6";
import http from "k6/http";

export const options = {
  thresholds: {},
  scenarios: {
//    _1000_ramping_VUs_30s: {
//      executor: "ramping-vus",
//      gracefulStop: "5s",
//      stages: [
//        { target: 1000, duration: "5s" },
//        { target: 1000, duration: "20s" },
//        { target: 0, duration: "5s" },
//      ],
//      gracefulRampDown: "5s",
//    },
    _100_constantVUs_3m: {
      executor: "constant-vus",
      gracefulStop: "5s",
      duration: "3m",
      vus: 100,
    },
  },
};

export default function () {
  //const res = http.get("http://localhost:8080/comments");
  //const res = http.get("http://localhost:8080/db/comments/1");
  //const res = http.get("http://localhost:8080/db/comments");
  //const res = http.get("http://localhost:8080/db/comments/page?page=1&size=10");
  //const res = http.get("http://localhost:8000/rsocket/comments");
  const res = http.get("http://localhost:8000/http/comments");

  check(res, {
    "is status 200": (r) => r.status === 200,
  });
}

// k6 run performance.js
// k6 run --vus <vus> --duration <duration> performance.js
