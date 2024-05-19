/*
 * PrayingTimes Calculator
 * Copyright (C) 2024 Mikhail Filatov
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

const fs = require('node:fs');

eval(fs.readFileSync('PrayTimes.js').toString())

prayTimes.setMethod("MWL")
prayTimes.adjust({ fajr: 16, isha: 15 })
const russia = prayTimes.getTimes(new Date(), [55.75222, 37.61556], 3);

prayTimes.setMethod("MWL")
const mwl = prayTimes.getTimes(new Date(), [55.75222, 37.61556], 3)

prayTimes.setMethod("Makkah")
const makkah = prayTimes.getTimes(new Date(), [55.75222, 37.61556], 3)

prayTimes.setMethod("Tehran")
const tehran = prayTimes.getTimes(new Date(), [55.75222, 37.61556], 3)

fs.writeFile('Russia.json', JSON.stringify(russia), err => {
    if (err) {
      console.error(err);
    } else {
      // file written successfully
    }
  });

  fs.writeFile('MWL.json', JSON.stringify(mwl), err => {
    if (err) {
      console.error(err);
    } else {
      // file written successfully
    }
  });

  fs.writeFile('Makkah.json', JSON.stringify(makkah), err => {
    if (err) {
      console.error(err);
    } else {
      // file written successfully
    }
  });

  fs.writeFile('Tehran.json', JSON.stringify(tehran), err => {
    if (err) {
      console.error(err);
    } else {
      // file written successfully
    }
  });
