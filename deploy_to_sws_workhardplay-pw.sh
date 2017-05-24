#!/bin/bash

echo "deploying to sws.workhardplay.pw" &&
    # lein clean &&
    lein with-profile dev filegen-ng &&
    lein scss :dev once &&
    LEIN_SNAPSHOTS_IN_RELEASE=true lein with-profile dev cljsbuild once &&
    scp -r ./resources/public/* deploy.do.myfutures.trade:/var/www/sws-workhardplay-pw &&
    echo "done"
    # ssh do.myfutures.trade "sudo service dev-myfutures-trade restart; sleep 10; sudo service dev-myfutures-trade status"
