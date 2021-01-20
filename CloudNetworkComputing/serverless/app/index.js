// Through CLI Version Final
var aws = require('aws-sdk');

// Create a new SES object in ap-south-1 region
var ses = new aws.SES({region: 'us-east-1'});
aws.config.update({region: 'us-east-1'});

// Create the DynamoDB service object
var ddb = new aws.DynamoDB.DocumentClient();

exports.handler = (event, context, callback) => {
       var email_details = event.Records[0].Sns.Message;
       var msgJson = JSON.parse(email_details);
       var messageId = event.Records[0].Sns.MessageId;
       console.log("Event Message ID: "+ messageId);
       console.log(event.Records[0]);
       var dynamoid = JSON.stringify(event.Records[0].Sns.Message);
       dynamoid = dynamoid.replace(/[^a-zA-Z0-9]/g, "")
        var getparams = {
        TableName: 'csye6225',
        Key: {
          'id': dynamoid
        }
      };
        
        ddb.get(getparams, function(err, data) {
       if (err) {
           console.error(" Error JSON:", JSON.stringify(err, null, 2));
       } else {
           
           if (data.Item == null)
           {
               writeEntry(dynamoid);  
                var params = {
                    Destination: {
                       ToAddresses: [msgJson.to_email1, msgJson.to_email2]
            
                    },
                    Message: {
                        Body: {
                            Text: { Data: msgJson.body 
                            }
                        },
                        Subject: { Data: msgJson.subject
                        }
                    },
                    Source: msgJson.from_email
                };
            
                console.log("calling email with params "+ params);
                ses.sendEmail(params, function (err, data) {
                    callback(null, {err: err, data: data});
                    if (err) {
                        console.log('This is error log');
                        console.log(err);
                        context.fail(err);
                    } else {
                        console.log('This is success log');
                        console.log(data);
                        context.succeed(event);
                    }
                });
                return context.logStreamName;
           }
           else{
               console.log("Email already sent");
           }

       }
    });
    
};


async function writeEntry(messageId) {
    console.log("messageId " + messageId);
    
      var putparams = {
                TableName: 'csye6225',
                Item: {
                  'id': messageId
            }
        };

    
    ddb.put(putparams, function(err, data) {
       if (err) {
           console.error(" Error JSON:", JSON.stringify(err, null, 2));
       } else {
           console.log("PutItem succeeded:");
       }
    });
}