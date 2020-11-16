var shell = require('shelljs')
var moment = require('moment')

const currentBranch = shell.exec(`git branch | grep -e "^*" | cut -d' ' -f 2`).stdout.trim()
const archiveTime = moment().format('YYYYMMDD_hhmm')

if (shell.exec('git pull').stdout.trim() !== 'Already up-to-date.') {
  console.log(`
    You just pull something from remote and you need to confirm your code is correct before you archive it.
    You can run this script again if you confirm this code is correct.
  `);

  shell.exit(1)
}

if (currentBranch) {

  printLog({
    start: `start script: 'npm run build'`,
    finish: `finished script: 'npm run build'`
  }, function () {
    shell.exec('npm run build')
  })

  printLog({
    start: `start script: 'git add -A && git commit -m 'build: ${archiveTime}' && git push`,
    finish: `finished script: 'git add -A && git commit -m 'build: ${archiveTime}' && git push'`
  }, function () {
    shell.exec(`git add -A && git commit -m 'build: ${archiveTime}' && git push`)
  })

  console.log('====================================');
  console.log(`currentBranch: ${currentBranch}`);
  console.log(`archiveTime: ${archiveTime}`);
  console.log(`Output: ./archive`);
  shell.exec(`git archive -o ./archive/dist_${archiveTime}.zip ${currentBranch}:dist/krungsri`)
  console.log('Archive success.')
  console.log('====================================');
}


function printLog(msg, callback) {
  console.log('====================================');
  console.log(msg.start);
  callback()
  console.log(msg.finish)
  console.log('====================================');
}
