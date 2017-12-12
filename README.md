# work-sessions-website

This is repo with source of [simultaneous work sessions](http://sws.eeefff.org/).

## lein targets

- ` lein figwheel` to start local server
- `lein release patch` to make new release before deployment
- `lein with-profile dev filegen-ng` to regenerate static files
- `lein scss :dev auto` to start SCSS autobuild

## Deployment

- see `./deploy_to_sws_eeefff_org.sh` for deployment details
- run `./deploy_to_sws_eeefff_org.sh && lein with-profile dev filegen-ng` if you don't want to stop figwheel
