var fs = require('fs');
eval(fs.readFileSync('PrayTimes.js').toString())
prayTimes.getTimes(new Date(), [55.75222, 37.61556], 3);
prayTimes.setMethod("MWL") 