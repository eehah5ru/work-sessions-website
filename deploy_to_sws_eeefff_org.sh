#!/bin/bash

echo "deploying to sws.eeefff.org" &&
    # lein clean &&
    lein with-profile production filegen-ng &&
    lein scss :dev once &&
    LEIN_SNAPSHOTS_IN_RELEASE=true lein with-profile production uberjar &&
    scp ./target/uberjar/work-sessions.jar work-sessions.do.myfutures.trade:/var/sws-eeefff-org &&
    ssh work-sessions.do.myfutures.trade "sudo service sws-eeefff-org restart; sleep 10; sudo service sws-eeefff-org status" &&
    curl http://sws.eeefff.org/proxy-viewer/show-docs &&
    lein with-profile dev filegen-ng
